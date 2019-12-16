package ca.georgebrown.gbcresearch.security

import ca.georgebrown.gbcresearch.Setting
import ca.georgebrown.gbcresearch.Submission
import ca.georgebrown.gbcresearch.ProfileSetting
import ca.georgebrown.gbcresearch.Tag

class Appuser {
    def settingService

	static final AUTHENTICATION_TYPE = [DAO:'DAO', LDAP: 'LDAP']
	static final SOURCE = [IAGO:'IAGO', BANNER: 'BANNER']

	static hasMany = [tags: Tag, following: Appuser, followedBy: Appuser, submissions: Submission]

	static hasOne = [setting: ProfileSetting]
	static mappedBy = [submissions: 'owner']
	transient springSecurityService

	String username
	String password = "nopass"

	String firstName
	String lastName
	String email

	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	boolean active

	String source = SOURCE.IAGO
	String authenticationType = AUTHENTICATION_TYPE.LDAP

	static transients = ['springSecurityService']


	static constraints = {
		username blank: false, unique: true
		password blank: false
		firstName nullable: true
		lastName nullable: true
		email nullable: true
		setting nullable: true
	}

	static mapping = {
		password column: '`password`'
		source defaultValue:  "'${SOURCE.IAGO}'"
	}

	Appuser(String username, String password) {
		this()
		this.username = username
		this.password = password
	}

	Set<Role> getAuthorities() {
		AppuserRole.findAllByAppuser(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	def scrollPreference() {
        if (setting.scrollSetting == ProfileSetting.SCROLL_SETTING.DEFAULT) {
            return settingService.get(Setting.CODE.SUBMISSION_SCROLL_SETTING)
        }
		return setting.scrollSetting
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	def getDisplayName() {
		return "${firstName} ${lastName}"
	}
}
