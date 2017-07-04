docker build . -t protractornet_buildenv
if((Get-WmiObject -class Win32_OperatingSystem).Caption.Contains("Server"))
{
    docker run -v c:\devgit\protractor-net:c:\workspace -it protractornet_buildenv powershell
}
else #Win10 Hyper-V Isolation for docker defaults to 1GB RAM instead of unlimited causing build failures
{
    docker run -m 2GB -v c:\devgit\protractor-net:c:\workspace -it protractornet_buildenv powershell
}