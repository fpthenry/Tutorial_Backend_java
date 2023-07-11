import groovy.transform.Field

@Field def useImage = true
@Field def useArtifact = true
@Field def isBackend = true
@Field def hasUnittest = true
@Field def harborProject = "test_shared_lib"
@Field def nexusGroupID = "test_shared_lib"
// @Field def projectFormat = "maven"
// @Field def isLibrary = true

def buildService() {
    stage('Build module back end'){
        try{
            def folder = sh(script: 'pwd', returnStdout: true)
            env.buildFolderResult = folder.trim()
            sh """
            docker pull 10.60.156.72/khcp/node:lts
            docker build -t 10.60.156.72/khcp/gpcp_roadmap:latest .
        """
        } catch(err){
            error "Build Failure"
        }
    }
}

def unitTestAndCodeCoverage(buildType){
    stage("Checkout source code"){
        jenkinsfile_utils.checkoutSourceCode(buildType)
    }

    stage("Unit Test & Code Coverage"){
        echo "Unit test"
        sh """
      # mvn test
      echo
    """
    }
}

def deployDevTest(version){
    echo "Version to deploy: $version"
    echo "Deploy to Test"
    // sh """
    //    deploy-ansible -p test_shared_lib -b dev -f deploy-test.yml
    // """
}


def deployStaging(version){
    echo "Version to deploy: $version"
    echo "Deploy to staging"
    // sh """
    //    deploy-ansible -p test_shared_lib -b staging -f deploy-staging.yml
    // """
}

def deployProduct(service,version){
    echo "Deploy to server Production"
    echo "Version to deploy: $version"
    echo "Deploy to product"
    // sh """
    //     sh prepare-images.sh 10.254.144.152/library/hello-world latest vtcloud /u01/images/
    //     ansible-playbook deploy-image.yaml -e HOSTS="vtcloud"
    // """
}

// For multitenant project
def deployProductTenant1(service,version){
    echo "Deploy to server Production Tenant1"
    echo "Version to deploy: $version"
    echo "Deploy to product Tenant1"
}

def fortifyScan(){
    jenkinsfile_utils.fortifyScanStage(
            [
                    serviceName : 'test-shared-lib',
                    sourcePathRegex : 'src\\main\\**\\*'
            ]
    )
}

def pushImage(){
    jenkinsfile_utils.pushImageToHarbor(
            [
                    // credentialID : "<place your Harbor credential ID stored in Jenkins here>", // using cicd_bot to push by default
                    repo_name : "khcp",
                    image_name : "10.60.156.72/khcp/gpcp_roadmap"
            ]
    )
}

def pushArtifact(){
    jenkinsfile_utils.pushArtifactToNexus(
            // [
            [
                    repo        : "Releases",
                    groupID     : "test_shared_lib",
                    artifactId  : "hello_world",
                    filepath    : "target/hello-coverage-0.1.0.jar"
            ]
            // [
            //   repo        : "Releases",
            //   groupID     : "test_shared_lib",
            //   artifactId  : "hello_world",
            //   filepath    : "target/hello-coverage-0.1.0.jar"
            // ]
            // ]
    )
}

def selfCheckService(){
    return true
}

def rollback(){
    echo "Define rollback plan here"
    return true
}

def autotestProduct(){
    if(env.autotestResult == "true"){
        return true
    }
    else {
        return false
    }
}

return this
