@echo off
@echo Building bpmLite beans

@call scomp -out bpmlite-beans.jar bpmlite.xsd -compiler "c:\Program Files\Java\jdk1.6.0_25\bin\javac.exe"
copy bpmlite-beans.jar ..\..\bpmLiteGuard\libs
copy bpmlite-beans.jar ..\..\bpmLite\libs

@echo all done.