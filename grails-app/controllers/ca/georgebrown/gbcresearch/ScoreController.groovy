package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import org.springframework.security.access.annotation.Secured


@Secured(['ROLE_APP_MANAGER','ROLE_DEVELOPER'])
class ScoreController {

    def springSecurityService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        Appuser appuser = springSecurityService.currentUser
        [appuser:appuser, weightFactorInstanceList: ScoreWeightingFactors.list(params), weightFactorInstanceTotal: ScoreWeightingFactors.count()]
    }

    def edit(Long id) {
        Appuser appuser = springSecurityService.currentUser
        def weightFactorInstance = ScoreWeightingFactors.get(id)
        if (!weightFactorInstance) {
            flash.message = "Weighting factor ${id} not found"
            redirect(action: "list")
            return
        }
        [appuser:appuser, weightFactorInstance: weightFactorInstance]
    }

    def update(Long id, Long version) {
        def weightFactorInstance = ScoreWeightingFactors.get(id)

        if (!weightFactorInstance) {
            flash.message = "Weighting factor ${id} not found"
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (weightFactorInstance.version > version) {
                weightFactorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        ['ScoreWeightingService'] as Object[],
                        "Another user has updated this weighting factor while you were editing")
                render(view: "edit", model: [weightFactorInstance: weightFactorInstance])
                return
            }
        }

        if (params.value instanceof Date) params.value = params.value.format(params.type == ScoreWeightingFactors.TYPE.Date ? "yyyy-M-d":"yyyy-M-d H:m:s")
        weightFactorInstance.properties = params

        if (!weightFactorInstance.save(flush: true)) {
            render(view: "list", model: [weightFactorInstance: weightFactorInstance])
            return
        }
        redirect(action: "list", id: weightFactorInstance.id)
    }
}
