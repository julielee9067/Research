package ca.georgebrown.gbcresearch.security

class RegistrationCode {
    String username
    String token = UUID.randomUUID().toString().replaceAll('-', '')
    Date dateCreated

    static mapping = {
        version false
    }
}
