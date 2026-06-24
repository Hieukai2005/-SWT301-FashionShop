$files = @(
    "src\java\dal\OrderDAO.java",
    "src\java\dal\ProductDAO.java",
    "src\java\dal\UserDAO.java",
    "src\java\dal\VoucherDAO.java"
)

foreach ($file in $files) {
    $fullPath = Join-Path -Path $PWD -ChildPath $file
    if (Test-Path $fullPath) {
        $content = [System.IO.File]::ReadAllText($fullPath, [System.Text.Encoding]::UTF8)
        
        # We need to replace """...""" blocks
        # Regex matching text blocks
        $pattern = '"""(?s)(.*?)"""'
        
        $evaluator = [System.Text.RegularExpressions.MatchEvaluator] {
            param($match)
            $block = $match.Groups[1].Value
            $lines = $block -split "`r?`n"
            
            # Remove empty first line
            if ($lines.Count -gt 0 -and $lines[0].Trim() -eq "") {
                $lines = $lines[1..($lines.Count-1)]
            }
            # Remove empty last line
            if ($lines.Count -gt 0 -and $lines[-1].Trim() -eq "") {
                $lines = $lines[0..($lines.Count-2)]
            }
            
            $result = @()
            for ($i = 0; $i -lt $lines.Count; $i++) {
                $line = $lines[$i].Replace("\", "\\").Replace("`"", "\`"")
                if ($i -lt $lines.Count - 1) {
                    $result += "`"$line\n`" +"
                } else {
                    $result += "`"$line`""
                }
            }
            
            if ($result.Count -eq 0) {
                return '""'
            }
            
            return ($result -join "`r`n")
        }
        
        $newContent = [System.Text.RegularExpressions.Regex]::Replace($content, $pattern, $evaluator)
        
        if ($newContent -ne $content) {
            [System.IO.File]::WriteAllText($fullPath, $newContent, [System.Text.Encoding]::UTF8)
            Write-Host "Converted $fullPath"
        }
    }
}
