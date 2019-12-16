package ca.georgebrown.gbcresearch

import grails.transaction.Transactional


@Transactional
class SubmissionTypeService {

    def update(params) {
        SubmissionType type = null
        params.name.each() { id,name ->
            type = SubmissionType.findById(id.toLong())
            type.name = name
            type.description = params.description[id]
            if (params.status) {
                type.status = params.status[id] == 'on' ? SubmissionType.STATUS.ACTIVE: SubmissionType.STATUS.INACTIVE
            }

            if (type.status && !params.status) {
                type.status = SubmissionType.STATUS.INACTIVE
            }

            type.save()
        }
    }

    def add(params) {
        SubmissionType type = new SubmissionType()
        type.name = params.new.type
        type.description = params.new.description
        type.save()
    }

    def get() {
        def types = SubmissionType.getAll()
        def AppliedResearchProject = [id: 0, name: 'Applied Research Project']
        types.add(AppliedResearchProject as SubmissionType)
        return types
    }
}