$files = Get-ChildItem -Path "d:\FPT\Subject\PRJ301_HoaNK\Assignment\fashionShop\src\java" -Filter "*.java" -Recurse

foreach ($file in $files) {
    $content = [System.IO.File]::ReadAllText($file.FullName, [System.Text.Encoding]::UTF8)
    if ($content -match "\.isBlank\(\)") {
        $newContent = $content -replace "\.isBlank\(\)", ".trim().isEmpty()"
        
        $utf8NoBom = New-Object System.Text.UTF8Encoding $false
        [System.IO.File]::WriteAllText($file.FullName, $newContent, $utf8NoBom)
        Write-Host "Replaced isBlank() in $($file.FullName)"
    }
}
