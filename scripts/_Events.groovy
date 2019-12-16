eventCompileStart = { msg ->
    def proc = "svnversion  --committed".execute()
    proc.waitFor()
    def templateHtml = '<span>Build Date:' + new Date().format('y-M-d HH:m') + " SVN:${proc?.in?.text?.trim()} </span>"
    new FileOutputStream("grails-app/views/_version.gsp", false) << templateHtml
}
