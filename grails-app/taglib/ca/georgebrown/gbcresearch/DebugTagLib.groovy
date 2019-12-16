package ca.georgebrown.gbcresearch

class DebugTagLib {
    static defaultEncodeAs = [taglib:'none']

    def sessionTestEmail = { attribs ->
       out << session.getAttribute('sessionEmail')
    }
}
