package ca.georgebrown.gbcresearch

import grails.transaction.Transactional
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.logging.LogFactory
import javax.naming.Context
import javax.naming.NamingEnumeration
import javax.naming.directory.Attribute
import javax.naming.directory.Attributes
import javax.naming.directory.SearchControls
import javax.naming.directory.SearchResult
import javax.naming.ldap.InitialLdapContext
import javax.naming.ldap.LdapContext
import javax.naming.ldap.StartTlsRequest
import javax.naming.ldap.StartTlsResponse


@Transactional
class LdapService {

    private static log = LogFactory.getLog(this)
    static def grailsApplication

    static def getAttributes(String username) {
        Map<String, String> ldapAttributes = new HashMap<String, String>()
        if (!username) return ldapAttributes

        String url = grailsApplication.mergedConfig.ldap.query.url
        boolean tls = grailsApplication.mergedConfig.ldap.query.tls
        String domain = grailsApplication.mergedConfig.ldap.query.domain
        String loginId = grailsApplication.mergedConfig.ldap.query.loginId
        String domainUsername = domain ? "${domain}\\${loginId}" : loginId
        String searchBase = grailsApplication.mergedConfig.ldap.query.base
        String searchFilter = grailsApplication.mergedConfig.ldap.query.filter.replace('{0}', username)
        int searchTimeLimit = grailsApplication.mergedConfig.ldap.query.timeLimit ?: 0
        String[] searchAttributes = grailsApplication.mergedConfig.ldap.query.attributes
        String password = grailsApplication.mergedConfig.ldap.query.password

        Hashtable<String, String> env = new Hashtable<String, String>()
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory")
        env.put(Context.PROVIDER_URL, url)
        LdapContext ctx = null;

        try {
            ctx = new InitialLdapContext(env)

            if (tls) ((StartTlsResponse) ctx.extendedOperation(new StartTlsRequest())).negotiate()

            ctx.addToEnvironment(Context.SECURITY_AUTHENTICATION, "simple")
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, domainUsername)
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password)
            ctx.getAttributes("", ["dnsHostName"] as String[])

            try {
                SearchControls constraints = new SearchControls(SearchControls.SUBTREE_SCOPE, 1L, searchTimeLimit, searchAttributes, false, false);
                NamingEnumeration<?> searchResults = ctx.search(searchBase, searchFilter, constraints);

                if (searchResults != null && searchResults.hasMore()) {
                    SearchResult searchResult = (SearchResult) searchResults.next()
                    Attributes attributes = searchResult.getAttributes()
                    if (attributes != null) {
                        for (NamingEnumeration enums = attributes.getAll(); enums.hasMore();) {
                            Attribute attribute = (Attribute) enums.next()
                            ldapAttributes.put(attribute.getID(), attribute.get())
                        }

                    }
                }
            }
            catch (Exception e) {
                // Ignore error and simply do not update database
            }
        }
        catch (Exception e) {
            log.error e
        }
        finally {
            if (ctx != null) {
                try {
                    ctx.close();
                }
                catch (Exception e) {
                    // Ignore Error
                }
            }
        }
        return ldapAttributes
    }

    def randomPassword() {
        int randomStringLength = 32
        String charset = (('a'..'z') + ('A'..'Z') + ('0'..'9')).join()
        String randomString = RandomStringUtils.random(randomStringLength, charset.toCharArray())
        return randomString
    }
}