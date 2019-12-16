package ca.georgebrown.gbcresearch

class SettingTagLib {
    static namespace = "setting"
    def settingService


    def get = { attrs, body ->
        if (!attrs.code) return
        out << settingService.get(attrs.code)
    }

}
