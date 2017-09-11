package com.medicine.framework.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.util.Assert;

import com.medicine.framework.entity.user.User;

public class MySessionRegistryImpl
        implements
        SessionRegistry,
        ApplicationListener<SessionDestroyedEvent> {
    // ~ Instance fields
    // ================================================================================================

    protected final Log logger = LogFactory.getLog(SessionRegistryImpl.class);

    /**
     * <principal:Object,SessionIdSet>
     */
    private final ConcurrentMap<Object, Set<String>> principals = new ConcurrentHashMap<Object, Set<String>>();

    /**
     * <sessionId:Object,SessionInformation>
     */
    private final Map<String, SessionInformation> sessionIds = new ConcurrentHashMap<String, SessionInformation>();

    // ~ Methods
    // ========================================================================================================

    public List<Object> getAllPrincipals() {
        return new ArrayList<Object>(principals.keySet());
    }

    public List<SessionInformation> getAllSessions(Object principal,
                                                   boolean includeExpiredSessions) {
        Set<String> sessionsUsedByPrincipal = null;

        for (Object obj : principals.keySet()) {
            User user1 = (User) principal;
            User user2 = (User) obj;

            if (user1.equals(user2) && user1.hashCode() == user2.hashCode()) {
                sessionsUsedByPrincipal = principals.get(principal);
            }
        }

        if (sessionsUsedByPrincipal == null) {
            return Collections.emptyList();
        }

        List<SessionInformation> list = new ArrayList<SessionInformation>(sessionsUsedByPrincipal.size());

        for (String sessionId : sessionsUsedByPrincipal) {
            SessionInformation sessionInformation = getSessionInformation(sessionId);

            if (sessionInformation == null) {
                continue;
            }

            if (includeExpiredSessions || !sessionInformation.isExpired()) {
                list.add(sessionInformation);
            }
        }

        return list;
    }

    public SessionInformation getSessionInformation(String sessionId) {
        Assert.hasText(sessionId,
                "SessionId required as per interface contract");

        return sessionIds.get(sessionId);
    }

    public void onApplicationEvent(SessionDestroyedEvent event) {
        String sessionId = event.getId();
        removeSessionInformation(sessionId);
    }

    public void refreshLastRequest(String sessionId) {
        Assert.hasText(sessionId,
                "SessionId required as per interface contract");

        SessionInformation info = getSessionInformation(sessionId);

        if (info != null) {
            info.refreshLastRequest();
        }
    }

    public void registerNewSession(String sessionId, Object principal) {
        Assert.hasText(sessionId,
                "SessionId required as per interface contract");
        Assert.notNull(principal,
                "Principal required as per interface contract");

        if (logger.isDebugEnabled()) {
            logger.debug("Registering session " + sessionId + ", for principal "
                    + principal);
        }

        if (getSessionInformation(sessionId) != null) {
            removeSessionInformation(sessionId);
        }

        sessionIds.put(sessionId,
                new SessionInformation(principal, sessionId, new Date()));

        Set<String> sessionsUsedByPrincipal = principals.get(principal);

        if (sessionsUsedByPrincipal == null) {
            sessionsUsedByPrincipal = new CopyOnWriteArraySet<String>();
            Set<String> prevSessionsUsedByPrincipal = principals
                    .putIfAbsent(principal, sessionsUsedByPrincipal);
            if (prevSessionsUsedByPrincipal != null) {
                sessionsUsedByPrincipal = prevSessionsUsedByPrincipal;
            }
        }

        sessionsUsedByPrincipal.add(sessionId);

        if (logger.isTraceEnabled()) {
            logger.trace("Sessions used by '" + principal + "' : "
                    + sessionsUsedByPrincipal);
        }
    }

    public void removeSessionInformation(String sessionId) {
        Assert.hasText(sessionId,
                "SessionId required as per interface contract");

        SessionInformation info = getSessionInformation(sessionId);

        if (info == null) {
            return;
        }

        if (logger.isTraceEnabled()) {
            logger.debug("Removing session " + sessionId
                    + " from set of registered sessions");
        }

        sessionIds.remove(sessionId);

        Set<String> sessionsUsedByPrincipal = principals
                .get(info.getPrincipal());

        if (sessionsUsedByPrincipal == null) {
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Removing session " + sessionId
                    + " from principal's set of registered sessions");
        }

        sessionsUsedByPrincipal.remove(sessionId);

        if (sessionsUsedByPrincipal.isEmpty()) {
            // No need to keep object in principals Map anymore
            if (logger.isDebugEnabled()) {
                logger.debug("Removing principal " + info.getPrincipal()
                        + " from registry");
            }
            principals.remove(info.getPrincipal());
        }

        if (logger.isTraceEnabled()) {
            logger.trace("Sessions used by '" + info.getPrincipal() + "' : "
                    + sessionsUsedByPrincipal);
        }
    }
}
