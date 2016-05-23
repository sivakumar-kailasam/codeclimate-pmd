#!/usr/bin/env groovy
import groovy.json.JsonSlurper
import groovy.util.FileNameFinder

def setupContext(cmdArgs) { 
	def cli = new CliBuilder(usage:"${this.class.name}")
	cli._(longOpt: "configFile", required: true, args: 1, "Path to configuration json file")
	cli._(longOpt: "codeFolder", required: true, args: 1, "Path to code folder")
	cli.parse(cmdArgs)
}

def appContext = setupContext(args)
assert appContext


def configJson = new JsonSlurper().parse(new File(appContext.configFile), "UTF-8")


def codeFolder = new File(appContext.codeFolder)
assert codeFolder.exists()


def includeString = configJson.include_paths?.join(" ")

if (includeString == null) {
   includeString = '**/*'
}

def scriptDir = getClass().protectionDomain.codeSource.location.path.replace("/${this.class.name}.groovy","")
def checkerDefinitionFromConfig = configJson.config
def checkerDefinitionFile = null
if (configJson.config?.trim()) {
	checkerDefinitionFile = new File(codeFolder, configJson.config)
} else {
    checkerDefinitionFile = new File(scriptDir.replace('/bin','/config'), 'codeclimate_pmd.xml')
}
assert checkerDefinitionFile.exists() && checkerDefinitionFile.isFile()

def filesToAnalyse = new FileNameFinder().getFileNames(appContext.codeFolder, includeString).join(",")

def pmdCommand = "${scriptDir}/pmd/bin/run.sh pmd -d ${filesToAnalyse} -f codeclimate -R ${checkerDefinitionFile} -v 35 -failOnViolation false"

ProcessBuilder builder = new ProcessBuilder(pmdCommand.split(' '))
Process process = builder.start()

InputStream stdout = process.getInputStream()
BufferedReader reader = new BufferedReader(new InputStreamReader(stdout))

while ((line = reader.readLine ()) != null) {
   System.out.println(line)
}

process.waitForProcessOutput()
System.exit(process.exitValue())
