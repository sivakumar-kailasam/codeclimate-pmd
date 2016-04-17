#!/usr/bin/env groovy

println "Hold on tight while we update pmd"

def releaseObject = new groovy.json.JsonSlurper().parseText(
		new URL("https://api.github.com/repos/pmd/pmd/releases/latest").getText()
		)

def latestAsset = releaseObject.assets.find { it.name.contains('bin') }
def assetUrl = latestAsset["browser_download_url"]
def assetName = latestAsset["name"]

def file = new File("$assetName")
file = file.newOutputStream()  
file << new URL(assetUrl).openStream()  
file.close()

new AntBuilder().unzip(src: assetName, dest: "", overwrite:false)
new File("pmd").deleteDir()
new File(assetName.replace(".zip", "")).renameTo("pmd")
new File(assetName).delete()
