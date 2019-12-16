package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.lists.ListInfo

class Select2HelperTagLib {
    static defaultEncodeAs = [taglib:'none']

    def setDisabled = { attribs ->
        attribs.list.each() {
            out <<     "\$('.${attribs.className} option[value=${it.id}]').attr('disabled', 'disabled');"

        }
    }
}
