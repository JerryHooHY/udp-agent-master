import os
import re

def remove_non_english_characters_from_file(file_path):
    """
    Removes all characters except English letters and common symbols from the given file and saves the modified content.

    Args:
        file_path (str): Path to the file.

    Returns:
        None
    """
    try:
        # Read the content of the file
        with open(file_path, 'r', encoding='utf-8') as file:
            content = file.read()

        # Use regex to retain only English letters and common symbols
        modified_content = re.sub(r'[^\x00-\x7F]+', '', content)

        # Save the modified content back to the same file
        with open(file_path, 'w', encoding='utf-8') as file:
            file.write(modified_content)

        print(f"Non-English characters have been successfully removed from '{file_path}'.")
    except Exception as e:
        print(f"An error occurred with file '{file_path}': {e}")

def remove_non_english_characters_from_directory(directory_path):
    """
    Removes all characters except English letters and common symbols from .m files in the given directory.

    Args:
        directory_path (str): Path to the directory.

    Returns:
        None
    """
    try:
        for root, _, files in os.walk(directory_path):
            for file in files:
                if file.endswith('.m'):
                    file_path = os.path.join(root, file)
                    remove_non_english_characters_from_file(file_path)
        print(f"All .m files in directory '{directory_path}' have been processed.")
    except Exception as e:
        print(f"An error occurred while processing the directory: {e}")

# Example usage
if __name__ == "__main__":
    directory_path = input("Enter the path to the directory: ").strip()
    remove_non_english_characters_from_directory(directory_path)
