@echo off
@echo Building bpmLite beans

@call scomp -out bpmlite-beans.jar bpmlite.xsd -compiler "c:\Program Files\Java\jdk1.6.0_25\bin\javac.exe"
REM copy bpmlite-beans.jar ..\..\bpmLiteGuard\libs
REM copy bpmlite-beans.jar ..\..\bpmLiteGuard\WebContent\WEB-INF\lib
REM copy bpmlite-beans.jar ..\..\bpmLite\libs
REM copy bpmlite-beans.jar ..\..\bpmLite\WebContent\WEB-INF\lib
REM copy bpmlite-beans.jar ..\..\bpmLiteTestClient\libs

@echo all done.