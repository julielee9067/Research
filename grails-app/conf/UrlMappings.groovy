class UrlMappings {

	static mappings = {

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }



        "/"(controller: 'loginSuccess', action: 'index')


        "404"(view:'/notFound')
        "500"(view:'/error')

    }
}
