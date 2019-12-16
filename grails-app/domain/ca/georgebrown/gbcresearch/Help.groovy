package ca.georgebrown.gbcresearch

class Help {
    String code
    String topic
    String htmlBody


    static mapping={
        htmlBody type:"text"
    }
    static constraints = {
        topic maxSize: 512
    }
}
