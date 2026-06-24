import os
import re

def convert_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Find all """ blocks
    pattern = re.compile(r'"""(.*?)"""', re.DOTALL)
    
    def replacer(match):
        block = match.group(1)
        lines = block.split('\n')
        # Remove first empty line if present
        if lines and lines[0].strip() == '':
            lines = lines[1:]
        # Remove last empty line if present
        if lines and lines[-1].strip() == '':
            lines = lines[:-1]
        
        result = []
        for i, line in enumerate(lines):
            # escape backslashes and double quotes
            escaped_line = line.replace('\\', '\\\\').replace('"', '\\"')
            if i < len(lines) - 1:
                result.append(f'"{escaped_line}\\n" +')
            else:
                result.append(f'"{escaped_line}"')
        
        if not result:
            return '""'
            
        return '\n'.join(result)

    new_content = pattern.sub(replacer, content)
    
    if new_content != content:
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(new_content)
        print(f"Converted {filepath}")

# Process specific DAOs
files_to_process = [
    r"src\java\dal\OrderDAO.java",
    r"src\java\dal\ProductDAO.java",
    r"src\java\dal\UserDAO.java",
    r"src\java\dal\VoucherDAO.java"
]

for file in files_to_process:
    full_path = os.path.join(r"d:\FPT\Subject\PRJ301_HoaNK\Assignment\fashionShop", file)
    if os.path.exists(full_path):
        convert_file(full_path)
    else:
        print(f"File not found: {full_path}")
