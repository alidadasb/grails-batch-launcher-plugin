/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author Daniel Henrique Alves Lima
 */
//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
//
//    ant.mkdir(dir:"${basedir}/grails-app/jobs")
//
includeTargets << new File("${batchLauncherPluginDir}/scripts/_BatchInit.groovy")

createLauncherFile = {->
    File f = new File("${basedir}/grails-app/conf/Launcher.groovy")
    if (!f.exists()) {
        try {
            String lf = System.properties['line.separator']
            f.createNewFile()
            f.withWriter('utf-8') {writer ->
                writer.print("\
/* Generated by batch-launcher plugin. */${lf}\
class Launcher {${lf}\
    def grailsApplication${lf}\
    ${lf}\
    def run = {/* Map */ context ->${lf}\
        // TODO: Remove unused code${lf}\
        println \"\${this.class}.run()\"${lf}\
        println \"args = \${context.args}\"${lf}\
        println \"app = \${grailsApplication}\"${lf}\
    }${lf}\
}${lf}")
            }

            event('CreatedFile', [f])
        } catch (Throwable e) {
            event('StatusError', [
                "Could not create ${f}: ${e}"
            ])
        }
    }
}

createLauncherFile()
