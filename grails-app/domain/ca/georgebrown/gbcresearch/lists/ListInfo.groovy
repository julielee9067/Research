package ca.georgebrown.gbcresearch.lists

class ListInfo {
    static final STATUS = [ENABLED: 'enabled', DISABLED: 'disabled']
    String domainName
    String description
    boolean isDefault = false
    int sortOrder = 100
    static constraints = {
    }
}
