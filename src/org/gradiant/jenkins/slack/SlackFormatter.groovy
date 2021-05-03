package org.gradiant.jenkins.slack


String format(String title = '', String message = '', String testSummary = '') {
    def helper = new JenkinsHelper()

    def project = helper.getProjectName()
    def branchName = helper.getBranchName()
    def buildNumber = helper.getBuildNumber()
    def url = helper.getAbsoluteUrl()
    def bo_url = ""
    def stage = ""

    def stage_raw = helper.getStageName()
    if (stage_raw != null) {
        stage = " - ${stage_raw}"
    }

    def result = "${project} ${stage}"

    def prefix = "branches/rudder/"
    if (branchName != null) {
        bo_url = " | <https://ci.normation.com/jenkins/blue/organizations/jenkins/${project}/detail/${branchName}/${buildNumber}/pipeline|BlueOcean>"

        if (branchName.startsWith(prefix)) {
            branchName = branchName.substring(prefix.size())
        }
    } else {
        bo_url = " | <https://ci.normation.com/jenkins/blue/organizations/jenkins/${project}/detail/${project}/${buildNumber}/pipeline|BlueOcean>"
    }

    if (branchName != null) result = "*${result}* >> ${branchName}"

    result = "${result} - #${buildNumber} ${title.trim()} (<${url}|Open> | <${url}consoleFull|Console>${bo_url})"
    if (message) result = result + "\nChanges:\n\t ${message.trim()}"
    if (testSummary) result = result + "\n ${testSummary}"

    return result
}
