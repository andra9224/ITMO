#include <stdio.h>
#include <stdlib.h>

// �������� ������� ����������
void encrypt(char* text, int key);

// �������� ������� ������������
void decrypt(char text[], int key);


int main(int argc, char* argv[]) {
    if (argc != 3) {
        printf("Usage: ./caesar <str> <key>\n");
        return 1;
    }

    char* text = argv[1];
    int key = atoi(argv[2]);

    printf("Encrypted text: %s\n", text);
    printf("Key: %d\n", key);

    // �������� ������� ��� ���������� ������������
    decrypt(text, key);

    printf("Decrypted text: %s\n", text);

    return 0;
}

// ���������� ������� ����������
void encrypt(char text[], int key) {
    for (int i = 0; text[i] != '\0'; i++) {
        char currentChar = text[i];

        if (currentChar >= 'a' && currentChar <= 'z') {
            char shiftedChar = 'a' + (currentChar - 'a' + key) % 26;
            text[i] = shiftedChar;
        }
        else if (currentChar >= 'A' && currentChar <= 'Z') {
            char shiftedChar = 'A' + (currentChar - 'A' + key) % 26;
            text[i] = shiftedChar;
        }
    }
}

//���������� ������� ������������
void decrypt(char text[], int key) {
    for (int i = 0; text[i] != '\0'; i++) {
        char currentChar = text[i];

        if (currentChar >= 'a' && currentChar <= 'z') {
            char shiftedChar = 'a' + (((currentChar - 'a' - key) % 26) + 26) % 26;
            text[i] = shiftedChar;
        }
        else if (currentChar >= 'A' && currentChar <= 'Z') {
            char shiftedChar = 'A' + (((currentChar - 'A' - key) % 26) + 26) % 26;
            text[i] = shiftedChar;
        }
    }
}