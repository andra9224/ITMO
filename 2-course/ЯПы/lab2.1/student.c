#include <stdlib.h>
#include <stdio.h>
#include <memory.h>

typedef enum {
	MALE,
	FEMALE
} GENDER;

typedef struct {
	char* name;
	int age;
	int course;
	GENDER gender;


} Student;

Student* createStudent(char* name, int age, int course, GENDER gender) {
    Student* student = (Student*)malloc(sizeof(Student));

    if (!student) {
        fprintf(stderr, "Memory allocation failed\n");
        return NULL;
    }

    student->name = name;
    student->age = age;
    student->course = course;
    student->gender = gender;

    return student;
}

char* createRandomName(void) {
    const char alphabet[] = "abcdefghijklmnopqrstuvwxyz";
    int minLen = 3;
    int maxLen = 8;

    int len = minLen + rand() % (maxLen - minLen + 1);

    char* name = malloc(len + 1);
    if (!name) return NULL;

    for (int i = 0; i < len; i++) {
        name[i] = alphabet[rand() % (sizeof(alphabet) - 1)];
    }
    name[len] = '\0';

    return name;
}

Student* createRandomStudent(void) {
    Student* student = (Student*)malloc(sizeof(Student));

    if (!student) {
        fprintf(stderr, "Memory allocation failed\n");
        return NULL;
    }

    student->name = createRandomName();
    student->age = 18 + rand() % (50 - 18 + 1);
    student->course = 1 + rand() % 4;
    student->gender = (rand() % 2 == 0) ? MALE : FEMALE;

    return student;

}

Student** createClass(int size) {
    Student** class = (Student**)malloc(size * sizeof(Student*));

    if (!class) {
        fprintf(stderr, "Memory allocation failed\n");
        return NULL;
    }

    for (int i = 0; i < size; i++) {
        class[i] = createRandomStudent();
    }

    return class;
}

void printStudent(const Student* student) {
    if (!student) {
        fprintf(stderr, "Invalid student\n");
        return;
    }

    const char* genderStr;
    switch (student->gender) {
    case MALE:
        genderStr = "Male";
        break;
    case FEMALE:
        genderStr = "Female";
        break;
    default:
        genderStr = "Unknown";
        break;
    }

    printf("Name: %s\n", student->name);
    printf("Age: %d\n", student->age);
    printf("Course: %d\n", student->course);
    printf("Gender: %s\n", genderStr);
}

void serializeStudent(const Student* student, const char* filename) {
    if (!student || !filename) {
        fprintf(stderr, "Invalid arguments for serialization\n");
        return;
    }

    FILE* file = fopen(filename, "wb");
    if (!file) {
        fprintf(stderr, "Failed to open file for writing\n");
        return;
    }

    fwrite(student, sizeof(Student), 1, file);
    fclose(file);
}

Student* deserializeStudent(const char* filename) {
    if (!filename) {
        fprintf(stderr, "Invalid filename for deserialization\n");
        return NULL;
    }

    FILE* file = fopen(filename, "rb");
    if (!file) {
        fprintf(stderr, "Failed to open file for reading\n");
        return NULL;
    }

    Student* student = (Student*)malloc(sizeof(Student));
    if (!student) {
        fprintf(stderr, "Memory allocation failed\n");
        fclose(file);
        return NULL;
    }

    fread(student, sizeof(Student), 1, file);
    fclose(file);

    return student;
}

int main() {
    Student* student = createRandomStudent();
    if (!student) {
        return -1;
    }
    printStudent(student);
    printf("\n");
    serializeStudent(student, "student.dat");
    free(student);
    Student* loadedStudent = deserializeStudent("student.dat");
    if (loadedStudent) {
        printStudent(loadedStudent);
        free(loadedStudent);
    }

    


    return 0;
}