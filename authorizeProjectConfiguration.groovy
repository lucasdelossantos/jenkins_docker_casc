/**
 * The script removes Build Authorization settings
 * (Jenkins - Configure Global Security - Access Control for Builds)
 * and creates:
 *   - Per-project 'Run as Specific User'
 *   - Default Project 'Run as User Who Triggered Build'
 *
 * Issue:
 * Authorize Project plugin doesn't full support JCasC plugin configuration
 * For more details, see: https://github.com/jenkinsci/authorize-project-plugin/pull/44
 */

import jenkins.model.Jenkins
import hudson.util.DescribableList

import jenkins.security.QueueItemAuthenticator
import jenkins.security.QueueItemAuthenticatorDescriptor
import jenkins.security.QueueItemAuthenticatorConfiguration

import org.jenkinsci.plugins.authorizeproject.GlobalQueueItemAuthenticator
import org.jenkinsci.plugins.authorizeproject.ProjectQueueItemAuthenticator

import org.jenkinsci.plugins.authorizeproject.strategy.AnonymousAuthorizationStrategy
import org.jenkinsci.plugins.authorizeproject.strategy.TriggeringUsersAuthorizationStrategy
import org.jenkinsci.plugins.authorizeproject.strategy.SpecificUsersAuthorizationStrategy
import org.jenkinsci.plugins.authorizeproject.strategy.SystemAuthorizationStrategy


Jenkins instance = Jenkins.get()

Map<String,Boolean> perProjectStrategyEnabledMap = [
    (instance.getDescriptor(AnonymousAuthorizationStrategy.class).getId()): false,
    (instance.getDescriptor(TriggeringUsersAuthorizationStrategy.class).getId()): false,
    (instance.getDescriptor(SpecificUsersAuthorizationStrategy.class).getId()): true,
    (instance.getDescriptor(SystemAuthorizationStrategy.class).getId()): false
]

DescribableList<QueueItemAuthenticator,QueueItemAuthenticatorDescriptor> authenticators = QueueItemAuthenticatorConfiguration.get().getAuthenticators()

authenticators.removeAll { it instanceof QueueItemAuthenticator }

authenticators.add(
    new ProjectQueueItemAuthenticator(perProjectStrategyEnabledMap)
)

authenticators.add(
    new GlobalQueueItemAuthenticator(
        new TriggeringUsersAuthorizationStrategy()
    )
)

instance.save()