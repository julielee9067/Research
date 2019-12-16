package ca.georgebrown.gbcresearch.security

import org.apache.commons.lang.builder.HashCodeBuilder

class AppuserRole implements Serializable {

	private static final long serialVersionUID = 1

	Appuser appuser
	Role role

	boolean equals(other) {
		if (!(other instanceof AppuserRole)) {
			return false
		}

		other.appuser?.id == appuser?.id &&
		other.role?.id == role?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (appuser) builder.append(appuser.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static AppuserRole get(long appuserId, long roleId) {
		AppuserRole.where {
			appuser == Appuser.load(appuserId) &&
			role == Role.load(roleId)
		}.get()
	}

	static boolean exists(long appuserId, long roleId) {
		AppuserRole.where {
			appuser == Appuser.load(appuserId) &&
			role == Role.load(roleId)
		}.count() > 0
	}

	static AppuserRole create(Appuser appuser, Role role, boolean flush = false) {
		def instance = new AppuserRole(appuser: appuser, role: role)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(Appuser u, Role r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = AppuserRole.where {
			appuser == Appuser.load(u.id) &&
			role == Role.load(r.id)
		}.deleteAll()

		if (flush) { AppuserRole.withSession { it.flush() } }

		rowCount > 0
	}

	static void removeAll(Appuser u, boolean flush = false) {
		if (u == null) return

		AppuserRole.where {
			appuser == Appuser.load(u.id)
		}.deleteAll()

		if (flush) { AppuserRole.withSession { it.flush() } }
	}

	static void removeAll(Role r, boolean flush = false) {
		if (r == null) return

		AppuserRole.where {
			role == Role.load(r.id)
		}.deleteAll()

		if (flush) { AppuserRole.withSession { it.flush() } }
	}

	static constraints = {
		role validator: { Role r, AppuserRole ur ->
			if (ur.appuser == null) return
			boolean existing = false
			AppuserRole.withNewSession {
				existing = AppuserRole.exists(ur.appuser.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['role', 'appuser']
		version false
	}
}
