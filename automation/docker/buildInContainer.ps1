subst w: c:\workspace
w:
Write-Host("Version passed in: $($args[0])")
.\AssemblyInfoPatchVersion.ps1 $($args[0])
nuget restore -Verbosity detailed
Set-Location .\src\Protractor
msbuild .\Protractor-NET40.csproj /target:Build /p:Configuration=Debug /p:Platform=AnyCPU
nuget pack -Version $($args[0])
