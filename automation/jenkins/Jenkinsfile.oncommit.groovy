@Library('buildmethods') _

String libName =      "Protractor"
String srcPath =      ".\\src\\${libName}"
String slnPath =      "${libName}-NET40.sln"
String artifactoryURL = "http://172.18.15.101:8081/artifactory"
String finalVersion = "0.10.3.${env.BUILD_NUMBER}"

node('bsswtrotmawin')
{
    //Colorize the Jenkins log to make it easier to read
    wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'vga']){

    try
    {
        stage("Checkout")
        {
            checkout scm
            bat "git clean -fdx"
        }

        stage("Build Container for .NET 4.52 Protractor Library Build")
        {
            dir(".\\automation\\docker")
            {
                bat "docker build . -t protractornet_buildenv"
            }
        }

        stage("Run Build Script In Container")
        {
            bat "docker run -v ${env.WORKSPACE}:c:\\workspace --rm protractornet_buildenv powershell -command \"& {cd .\\workspace\\automation\\docker; .\\buildInContainer.ps1 ${finalVersion}}\""
        }

        stage("Archive Package")
        {
            //archiveArtifacts artifacts: '**/Protractor*.nupkg', fingerprint: true
        }

        //publishToArtifactory(libName, libName, finalVersion)

    }
    catch(err)
        {
           String teamEmail = "rdsemisonic@renaissance.com"
            String from = teamEmail
            String to = teamEmail
            String subject = "${env.JOB_NAME} Build ${env.BUILD_NUMBER} Failure"
            String body = "Build Log: ${env.BUILD_URL}consoleFull" + "\r\n\r\n" + "Test Report: ${env.BUILD_URL}testReport" + "\r\n\r\nError Thrown: " + err
            String replyTo = teamEmail
            mailto(from,to,subject,body,replyTo)
            postToFlowdock("96d01f7d005455418fe89297ac1b2dc0",body)
            throw err
        }
}}
