package ca.georgebrown.gbcresearch

import grails.transaction.Transactional


@Transactional
class TagService {

    def update(params) {
        Tag tag
        Tag existing

        params.name.each() { id, name ->
            existing = Tag.findByName(name)
            tag = Tag.findById(id.toLong())

            if (tag) {
                if (!existing) {
                    tag.name = name
                    tag.submissions.add(params?.submission)
                }

                if (tag.status && !params.status) {
                    tag.status = Tag.STATUS.INACTIVE
                }

                tag.save(flush: true)

                if (params.status) {
                    if (params.status[id] == 'on' && tag.status == Tag.STATUS.INACTIVE) {
                        tag.setTagStatus(Tag.STATUS.ACTIVE)
                    } else if (!params.status[id] && tag.status == Tag.STATUS.ACTIVE)  {
                        tag.setTagStatus(Tag.STATUS.INACTIVE)
                    }
                }
            }
        }
        return tag
   }

    def add(params) {
        Tag tag = Tag.findByName(params.new.name)

        if (!tag) {
            tag = new Tag(name: params.new.name)
            tag.submissions = []
            tag.status = Tag.STATUS.ACTIVE
            tag.save(flush: true)
        }

        tag.submissions.add(params?.submission)
        return tag
    }
}
