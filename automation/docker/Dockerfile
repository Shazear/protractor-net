FROM microsoft/windowsservercore

SHELL ["powershell"]

RUN '172.18.15.101 artifactory.wrpwi.root.local' | Add-Content -PassThru C:\windows\System32\drivers\etc\hosts
RUN '172.18.13.3 teamcity.renlearn.com' | Add-Content -PassThru C:\windows\System32\drivers\etc\hosts
RUN Get-Content c:\windows\system32\drivers\etc\hosts
RUN Start-Bitstransfer "http://172.18.15.101:8081/artifactory/devops-configuration/installchocolatey.ps1" \
    -Destination c:\users\ContainerAdministrator\Downloads\installchocolatey.ps1

RUN c:\users\ContainerAdministrator\Downloads\installchocolatey.ps1

RUN Start-Bitstransfer "http://artifactory.wrpwi.root.local:8081/artifactory/devops-configuration/NDP452-KB2901951-x86-x64-DevPack.exe" \
    -Destination C:\users\ContainerAdministrator\Downloads

RUN cmd /c C:\users\ContainerAdministrator\Downloads\NDP452-KB2901951-x86-x64-DevPack.exe /Quiet /Norestart

RUN choco install -y rlnugetsources \
                     rlinunit \
                     nuget.commandline

RUN Start-Bitstransfer "http://artifactory.wrpwi.root.local:8081/artifactory/devops-configuration/buildtools2015U3_full.exe" \
    -Destination C:\users\ContainerAdministrator\Downloads

RUN cmd /c C:\users\ContainerAdministrator\Downloads\buildtools2015U3_full.exe /Quiet /Norestart

# Add msbuild to path
# Replace with `ENV` when docker fixes it for windows. See https://github.com/docker/docker/issues/22017
RUN "[Environment]::SetEnvironmentVariable('Path', $env:Path + ';C:\Program Files (x86)\MSBuild\14.0\bin', [EnvironmentVariableTarget]::Machine)"

RUN Set-ExecutionPolicy Bypass -Force