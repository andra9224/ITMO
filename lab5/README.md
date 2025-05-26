Программа для управления коллекцией фильмов.  
Позволяет добавлять, обновлять, удалять и фильтровать фильмы, сохранять и загружать коллекцию в формате JSON, а также выполнять команды из скриптовых файлов.

---

##  Как запустить

java -jar MovieManager.jar data.json

Где data.json - JSON-файл в который будет сохранена коллекция

## Поддерживаемые команды

- `help` — справка по доступным командам
  
![Снимок экрана 2025-05-04 090036](https://github.com/user-attachments/assets/1b2c2a64-78ab-4d77-9e0e-ba189b0324fb)
- `add` — добавить фильм
  
![Снимок экрана 2025-05-04 090410](https://github.com/user-attachments/assets/5652e95c-dd82-4e23-a955-a5a9e325021a)
![Снимок экрана 2025-05-04 090424](https://github.com/user-attachments/assets/3eebcaca-948a-4fc4-aaac-6734ea5c1e0c)
- `add_if_max` — добавить фильм, если его значение больше всех
  
![Снимок экрана 2025-05-04 090703](https://github.com/user-attachments/assets/5e384acb-87ba-4866-8205-783496b931b5)
- `add_if_min` — добавитьфильм, если его значение меньше всех
  
![Снимок экрана 2025-05-04 090909](https://github.com/user-attachments/assets/621974ab-4c1d-443f-874b-4a02a934f42e)
![Снимок экрана 2025-05-04 090940](https://github.com/user-attachments/assets/c31b6321-254f-44ff-b638-2ae37d73292a)
- `execute_script {file}` — выполнить команды из файла
  
![Снимок экрана 2025-05-04 091408](https://github.com/user-attachments/assets/3f0549eb-20bd-4581-a4fb-c8d5bbf0e52f) ![Снимок экрана 2025-05-04 091431](https://github.com/user-attachments/assets/7aacdb0f-5fed-43cb-a1b3-eabc7bae9901)
- `show` — вывести все фильмы в строковом представлении
  
![Снимок экрана 2025-05-04 091556](https://github.com/user-attachments/assets/a9e5313b-04b7-4e55-8e89-202291a0ed2c)
- `info` — информация о коллекции

![Снимок экрана 2025-05-04 091610](https://github.com/user-attachments/assets/70ad6095-1446-44d1-a593-783b0205bae7)
- `save` — сохранить коллекцию в файл

![Снимок экрана 2025-05-04 091636](https://github.com/user-attachments/assets/f8e5b53f-9119-40c0-be0f-9338b0b1cc55)

![json1](https://github.com/user-attachments/assets/58411b46-9049-44f5-bd3a-ffc0127bfb3a) ![Снимок экрана 2025-05-04 091720](https://github.com/user-attachments/assets/ae773bb2-a9cc-40c7-bd09-4a9f5fcf19e1)
- `min_by_usa_box_office` — вывести любой фильм из коллекции, имеющий минимальные кассовые сборы в США
  
![Снимок экрана 2025-05-04 091813](https://github.com/user-attachments/assets/b6dcf4c1-d6e9-4761-8590-05b3d56d2d53)
- `count_less_than_length {length}` — вывести количество фильмов, короче заданного значения
  
![Снимок экрана 2025-05-04 091900](https://github.com/user-attachments/assets/1d603829-be42-49fb-a1de-54634280db6b)
- `filter_starts_with_name {name}` — вывести фильмы, имя которых начинается с заданной подстроки
  
![Снимок экрана 2025-05-04 091954](https://github.com/user-attachments/assets/5282b56a-e111-4aea-930c-68ba5eb8264a)
- `update {id}` — обновить фильм по id
  
![Снимок экрана 2025-05-04 092155](https://github.com/user-attachments/assets/43c37a55-0e82-4608-affc-9f74a4900e76)
- `remove_by_id {id}` — удалить по id

![Снимок экрана 2025-05-04 092246](https://github.com/user-attachments/assets/289fedff-6044-41e1-9329-011c4ba1998d)
- `remove_lower` — удалить все фильмы меньше заданного
  
![Снимок экрана 2025-05-04 093310](https://github.com/user-attachments/assets/cc342941-6e00-43f5-88aa-73418f4b8a87)
- `clear` — очистить коллекцию

![Снимок экрана 2025-05-04 093325](https://github.com/user-attachments/assets/4834d261-a90c-4bd5-a1c1-9899efe82e1d)
- `exit` — завершить программу

![Снимок экрана 2025-05-04 152438](https://github.com/user-attachments/assets/d8916798-3a90-439b-a91b-fe3b3d8706fe)
