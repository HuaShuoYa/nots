* 旧版打包规则

```groovy
applicationVariants.all { variant ->
                variant.outputs.each { output ->
                    def outputFile = output.outputFile
                    if (outputFile != null && outputFile.name.endsWith('.apk')) {
                      
                        def flavorName = variant.flavorName.startsWith("_") ? variant.flavorName.substring(1) : variant.flavorName
                        def fileName = "CheckInRecord_v${variant.versionName}.apk"
                        output.outputFile = new File(outputFile.parent, fileName)
                    }
                }
            }
```

* 新版打包规则

```groovy
android.applicationVariants.all { variant ->
                variant.outputs.all {
                    outputFileName = "Announcement_v${variant.versionName}.apk"
                }
            }
```

