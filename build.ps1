[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$ErrorActionPreference = "Stop"

$scriptDir = $PSScriptRoot
if ([string]::IsNullOrEmpty($scriptDir)) { $scriptDir = (Get-Location).Path }
$base = $scriptDir
$projectRoot = Split-Path $base -Parent

$pclRoot = Get-ChildItem "D:\" -Directory | Where-Object { $_.Name -like "PCL*" } | Select-Object -First 1
if (-not $pclRoot) { throw "PCL root not found on D:\" }
$mc = Join-Path $pclRoot.FullName ".minecraft"
$lib = Join-Path $mc "libraries"

function Find-FirstJar([string]$pattern) {
    $hit = Get-ChildItem $lib -Recurse -Filter $pattern -ErrorAction SilentlyContinue | Select-Object -First 1
    if (-not $hit) { throw "Missing library jar: $pattern" }
    return $hit.FullName
}

$mcSrg = Find-FirstJar "client-1.21.1-*-srg.jar"
$neoforgeUniv = Find-FirstJar "neoforge-21.1.243-universal.jar"
$neoforgeClient = Find-FirstJar "neoforge-21.1.243-client.jar"
$neoforgeLoader = Find-FirstJar "loader-4.0.42.jar"
$neoforgeBus = Find-FirstJar "bus-8.0.5.jar"

$modsDir = Join-Path $mc "versions\Infinity Legacy II\mods"
$projecte = Get-ChildItem $modsDir -Filter "ProjectE-*.jar" | Select-Object -First 1
$mekanism = Get-ChildItem $modsDir -Filter "Mekanism-*.jar" | Where-Object { $_.Name -notmatch "Generators|Tools|Extras|unleashed|kubejs|mekanismcovers" } | Select-Object -First 1
$ae2lt = Get-ChildItem $projectRoot -Filter "ae2lt-*.jar" | Select-Object -First 1
$ae2 = Get-ChildItem $modsDir -Filter "appliedenergistics2-*.jar" | Select-Object -First 1
$bigreactors = Get-ChildItem $projectRoot -Filter "ExtremeReactors2-*.jar" | Select-Object -First 1
if (-not $bigreactors) { $bigreactors = Get-ChildItem $modsDir -Filter "ExtremeReactors2-*.jar" | Select-Object -First 1 }
$zerocore = Get-ChildItem $modsDir -Filter "ZeroCore2-*.jar" | Select-Object -First 1
$ae2cs = Get-ChildItem $projectRoot -Filter "ae2cs-*.jar" | Select-Object -First 1
$ae2omnicells = Get-ChildItem $projectRoot -Filter "ae2omnicells-*.jar" | Select-Object -First 1
$dataEnergistics = Get-ChildItem $projectRoot -Filter "data_energistics-*.jar" | Select-Object -First 1
$mekanismExtras = Get-ChildItem $projectRoot -Filter "mekanism_extras-*.jar" | Select-Object -First 1
$mekanismsun = Get-ChildItem $projectRoot -Filter "mekanismsun-*.jar" | Select-Object -First 1
$neoecoae = Get-ChildItem $projectRoot -Filter "neoecoae-*.jar" | Select-Object -First 1
$pei = Get-ChildItem $projectRoot -Filter "*ProjectE_Integration*.jar" | Select-Object -First 1
if (-not $projecte) { throw "ProjectE jar not found in mods folder" }
if (-not $mekanism) { throw "Mekanism jar not found in mods folder" }
if (-not $ae2lt) { throw "ae2lt jar not found in $projectRoot" }
if (-not $ae2) { throw "AE2 jar not found in mods folder" }
if (-not $bigreactors) { throw "ExtremeReactors2 jar not found" }
if (-not $zerocore) { throw "ZeroCore2 jar not found in mods folder" }
if (-not $ae2cs) { throw "ae2cs jar not found in $projectRoot" }
if (-not $ae2omnicells) { throw "ae2omnicells jar not found in $projectRoot" }
if (-not $dataEnergistics) { throw "data_energistics jar not found in $projectRoot" }
if (-not $mekanismExtras) { throw "mekanism_extras jar not found in $projectRoot" }
if (-not $mekanismsun) { throw "mekanismsun jar not found in $projectRoot" }
if (-not $neoecoae) { throw "neoecoae jar not found in $projectRoot" }
if (-not $pei) { throw "PEI jar not found in $projectRoot" }

Write-Output "MC srg:      $mcSrg"
Write-Output "NeoForge:    $neoforgeUniv"
Write-Output "ProjectE:    $($projecte.FullName)"
Write-Output "Mekanism:    $($mekanism.FullName)"
Write-Output "AE2LT:       $($ae2lt.FullName)"
Write-Output "AE2:         $($ae2.FullName)"
Write-Output "BigReactors: $($bigreactors.FullName)"
Write-Output "ZeroCore:    $($zerocore.FullName)"
Write-Output "AE2CS:       $($ae2cs.FullName)"
Write-Output "AE2Omni:     $($ae2omnicells.FullName)"
Write-Output "DataEng:     $($dataEnergistics.FullName)"
Write-Output "MekExtras:   $($mekanismExtras.FullName)"
Write-Output "MekSun:      $($mekanismsun.FullName)"
Write-Output "NeoEcoAE:    $($neoecoae.FullName)"
Write-Output "PEI:         $($pei.FullName)"

$allLibs = Get-ChildItem $lib -Recurse -Filter "*.jar" -ErrorAction SilentlyContinue | Where-Object {
    $_.FullName -notmatch "\\net\\minecraft\\" -and
    $_.FullName -notmatch "\\net\\neoforged\\" -and
    $_.FullName -notmatch "\\net\\minecraftforge\\" -and
    $_.FullName -notmatch "\\net\\fabricmc\\" -and
    $_.FullName -notmatch "\\org\\spongepowered\\"
} | ForEach-Object { $_.FullName }

$cp = @($mcSrg, $neoforgeUniv, $neoforgeClient, $neoforgeLoader, $neoforgeBus) + $allLibs + @($projecte.FullName, $mekanism.FullName, $ae2lt.FullName, $ae2.FullName, $bigreactors.FullName, $zerocore.FullName, $ae2cs.FullName, $ae2omnicells.FullName, $dataEnergistics.FullName, $mekanismExtras.FullName, $mekanismsun.FullName, $neoecoae.FullName, $pei.FullName)
$cpStr = $cp -join ";"

$classesDir = Join-Path $base "build\classes"
$pkgDir = Join-Path $base "build\package"
New-Item -ItemType Directory -Force -Path $classesDir | Out-Null
New-Item -ItemType Directory -Force -Path $pkgDir | Out-Null

$srcs = Get-ChildItem (Join-Path $base "src") -Recurse -Filter "*.java" | ForEach-Object { $_.FullName.Replace("\", "/") }
if (-not $srcs) { throw "No java sources found under src" }

Write-Output "Compiling $($srcs.Count) source files..."
$argFile = Join-Path $base "build\javac_args.txt"
$cpFwd = $cpStr.Replace("\", "/")
$classesFwd = $classesDir.Replace("\", "/")
$argLines = @(
    "--release", "21",
    "-encoding", "UTF-8",
    "-proc:none",
    "-cp", "`"$cpFwd`"",
    "-d", "`"$classesFwd`""
) + ($srcs | ForEach-Object { "`"$_`"" })
$utf8NoBom = New-Object System.Text.UTF8Encoding($false)
[System.IO.File]::WriteAllLines($argFile, $argLines, $utf8NoBom)
& javac "@$argFile"
if ($LASTEXITCODE -ne 0) { throw "javac failed" }
Write-Output "Compilation OK."

Copy-Item -Recurse -Force (Join-Path $base "META-INF") $pkgDir
Copy-Item -Recurse -Force (Join-Path $base "data") $pkgDir
Copy-Item -Recurse -Force "$classesDir\*" $pkgDir

$nameFile = Join-Path $base "jar_name.txt"
if (-not (Test-Path $nameFile)) { throw "jar_name.txt not found in $base" }
$jarName = [System.IO.File]::ReadAllText($nameFile, [System.Text.Encoding]::UTF8).Trim()
$outJar = Join-Path (Join-Path $base "output") $jarName
Set-Location $pkgDir
& jar cf $outJar .
if ($LASTEXITCODE -ne 0) { throw "jar packaging failed" }
Write-Output "Packaged: $outJar"
