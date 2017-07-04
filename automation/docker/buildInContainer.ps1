subst w: c:\workspace
w:
nuget restore -Verbosity detailed
Set-Location .\src\Protractor
#set version number in assemblyinfo and nuspec
msbuild .\Protractor-NET40.csproj /target:Build /p:Configuration=Release /p:Platform=AnyCPU
nuget pack
