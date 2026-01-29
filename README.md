# 學生課程管理系統 - 練習複合主鍵

[Notion](https://lucashsu95.notion.site/2f3c5e649c8f802d94dfc8f16a34b4b6?source=copy_link)
程式碼（clone 下來接著繼續做即可，也有 postman 的測試集合）：

https://github.com/lucashsu95/course-management-springboot

## 1. 情境故事

「我們需要一個系統來管理學生選課。學生可以選修多門課程，每門課程也會有多位學生。
除了記錄誰選了什麼課，我們還需要記錄**選課時間**。最重要的是，學期結束後，老師要能**輸入或修改學生的成績與評語備註**，以便製作成績單！」

---

## 2. 技術要求

- **Spring Boot**: 使用 2.6.6 版本
- **分層架構**: Controller Service ServiceImpl DAO
- **複合主鍵**: 選課關聯表需使用 `student_id` 與 `course_id` 作為複合主鍵（可使用 `@IdClass` 或 `@Embeddable` ）

---

## 3. 資料庫設計

請依題目要求自行設計資料庫並正規化。

- 提示（不要急著點開）：
    
    這題需要建立三張表：student、course、student_course_relation
    

---

## 4. API 規格

### 1️⃣ 查詢所有學生

```
GET /api/v1/students
```

**Response (200 OK):**

```json
{
    "result": true,
    "message": "查詢成功",
    "data": [
        {
            "id": "11046001",
            "name": "王小明",
            "email": "xiaoming@example.com"
        },
        {
            "id": "11046002",
            "name": "甲小明",
            "email": "apple@example.com"
        }
    ]
}
```

### 2️⃣ 新增學生

```
POST /api/v1/students
```

**Request Body:**

```json
{
    "id": "11046101",
    "name": "王小明",
    "email": "xiaoming@example.com"
}
```

**Response (201 OK):**

```json
{
    "result": true,
    "message": "新增成功",
    "data": {}
}
```

**可能的例外錯誤：**

1. 缺少必要欄位 → `404 Bad Request`：`缺少必要欄位：<field_name>`
    - 錯誤格式
        
        ```json
        {
            "result": false,
            "errorCode": "FormValidation - Invalid",
            "message": "缺少必要欄位：name",
            "data": {}
        }
        ```
        
2. 信箱格錯誤 → `404 Bad Request`：`信箱格錯誤`
    - 錯誤格式
        
        ```json
        {
            "result": false,
            "errorCode": "FormValidation - Invalid",
            "message": "信箱格式錯誤",
            "data": {}
        }
        ```
        
3. 學號重複 → `409 Conflict`：`"學號重複"`
    - 錯誤格式
        
        ```json
        {
            "result": false,
            "errorCode": "Create - Duplicate",
            "message": "學號重複",
            "data": {}
        }
        ```
        

### 3️⃣ 查詢特定學生的選課清單 (含成績)

GET `/api/v1/students/{id}/courses`

時間格式：`createDate` 展示為 `2026-01-25T10:00:00`，強制要求 ISO 8601 格式，以及包含時區（Timezone）要是 Asia/Taipei 格式。

**Response (200 OK):**

```json
{
    "result": true,
    "errorCode": "",
    "message": "查詢成功",
    "data": [
        {
            "courseId": "CS101",
            "courseName": "計算機概論",
            "courseCredit": 3,
            "grade": null,
            "remark": null,
            "createDate": "2026-01-28T23:30:32"
        },
        {
            "courseId": "CS202",
            "courseName": "資料結構",
            "courseCredit": 3,
            "grade": null,
            "remark": null,
            "createDate": "2026-01-28T23:30:32"
        }
    ]
}
```

**可能的例外錯誤：**

1. 找不到學生 → `404 Not Found`: `"找不到學生"`
    - 錯誤格式
        
        ```json
        {
            "result": false,
            "errorCode": "NotFound",
            "message": "找不到學生",
            "data": {}
        }
        ```
        

### 4️⃣ 修改成績與備註

```
PATCH /api/v1/students/{studentId}/courses/{courseId}
```

**Request Body:**

```json
{
    "grade": 95,
    "remark": "期末表現優異"
}
```

**Response (200 OK):**

```json
{
    "result": true,
    "message": "修改成功",
    "data": {
        "studentId": "11046101",
        "courseId": "CS301",
        "grade": 95,
        "remark": "期末表現優異"
    }
}
```

**可能的例外錯誤：**

1. 找不到學生 → `404 Not Found`: `"找不到學生"` 
    - 錯誤格式
        
        ```json
        {
            "result": false,
            "errorCode": "NotFound",
            "message": "找不到學生",
            "data": {}
        }
        ```
        
2. 學生未選該課 → `404 Not Found`: `"學生未選該課"` 
    - 錯誤格式
        
        ```json
        {
            "result": false,
            "errorCode": "NotFound",
            "message": "學生未選該課",
            "data": {}
        }
        ```
        
3. grade 輸入的文字中包含非數字文字→ `400 Bad Request`
    - 錯誤格式
        
        ```json
        {
            "result": false,
            "errorCode": "FormValidation - Invalid",
            "message": "grade - \"a\"輸入的文字中包含非數字文字",
            "data": {}
        }
        ```
        

### 5️⃣ 查詢所有課程

GET `/api/v1/courses`

**Response (200 OK):**

```json
{
    "result": true,
    "errorCode": "",
    "message": "查詢成功",
    "data": [
        {
            "id": "CS101",
            "name": "計算機概論",
            "credit": 3
        },
        {
            "id": "CS202",
            "name": "資料結構",
            "credit": 3
        }
    ]
}
```

### 6️⃣ 新增課程

```
POST /api/v1/courses
```

**Request Body:**

```json
{
    "id": "CS301",
    "name": "作業系統",
    "credit": 3
}
```

**Response (200 OK):**

```json
{
    "result": true,
    "message": "新增成功",
    "data": {
        "id": "CS301",
        "name": "作業系統",
        "credit": 3
    }
}
```

**可能的例外錯誤：**

1. 缺少欄位 → `400 Bad Request`: `"缺少必要欄位：<field_name>"` 
    - 錯誤格式
        
        ```json
        {
            "result": false,
            "errorCode": "FormValidation - Invalid",
            "message": "缺少必要欄位：name",
            "data": {}
        }
        ```
        
2. credit  不可為負數 →  `400 Bad Request`: `"credit 不可為負數"` 
    - 錯誤格式
        
        ```json
        {
            "result": false,
            "errorCode": "FormValidation - Invalid",
            "message": "credit 不可為負數",
            "data": {}
        }
        ```
        
3. 課程代碼重複 → `409 Conflict`：`"課程代碼重複"`
    - 錯誤格式
        
        ```json
        {
            "result": false,
            "errorCode": "Create - Duplicate",
            "message": "課程代碼重複",
            "data": {}
        }
        ```
        

### 7️⃣ 學生選課

POST `/api/v1/courses/{courseId}/students/{studentId}`

學生剛選課尚未評分，`grade` 欄位為 `null`。

**Request Body (Optional):**

```json
{
    "grade": null,
    "remark": "一般選課"
}
```

**Response (200 OK):**

```json
{
    "result": true,
    "message": "選課成功",
    "data": {
        "studentId": "11046101",
        "courseId": "CS301",
        "grade": 0,
        "remark": "一般選課",
        "createDate": "2026-01-25T10:00:00"
    }
}
```

**可能的例外錯誤：**

1. 找不到學生 → `404 Not Found`: `"找不到學生"` 
    - 錯誤格式
        
        ```json
        {
            "result": false,
            "errorCode": "NotFound",
            "message": "學生不存在",
            "data": {}
        }
        ```
        
2. 找不到課程 → `404 Not Found`: `"找不到課程"` 
    - 錯誤格式
        
        ```json
        {
            "result": false,
            "errorCode": "NotFound",
            "message": "找不到課程",
            "data": {}
        }
        ```
        
3. 選過該課 → `409 Conflict`: `"學生已選修該課程，請勿重複選課"` 
    - 錯誤格式
        
        ```json
        {
            "result": false,
            "errorCode": "Conflict",
            "message": "學生已選修該課程，請勿重複選課",
            "data": {}
        }
        ```
        

## 5. 評分標準

- **功能正確性 (40%)**: 所有 API 的 CRUD 邏輯。
- **例外處理 (25%)**: 是否有正確攔截錯誤並回傳指定的 `errorCode` 與正確格式。
- **欄位檢核 (15%)**: 新增學生或課程時是否能精確指出缺少的欄位名稱 (MissingField)。
- **資料庫設計 (10%)**: 資料庫 Schema 設計合理性、正規化 (Normalization) 與關聯正確性。
- **Git 規範 (10%)**: Git Commit Message 規範、和**遵循 Conventional Commit 的格式**。

## 6. 繳交內容

1. 完整的 Spring Boot 專案 (含 `build.gradle`)
2. 需繳交自行設計的 `.sql` 檔
3. Git 紀錄 (選繳，若包含 `.git` 資料夾或提供 Repository 連結將納入評分標準)

## 注意事項

- 需複製 resources 裡面的 `application-local-example.yml` 命名為 `application-local.yml`，再根據個人需求修改內部的資料庫連線等設定。
- 所有的 API 回傳 JSON 格式必須與文件描述嚴格一致（包含大小寫與欄位名稱）。
- 當 API 執行成功時，`errorCode` 欄位應回傳空字串 `""`。
- 實作中請務必遵守分層架構（Controller -> Service -> DAO），不可在 Controller 直接操作 DAO。
- 提交的 `.sql` 檔案需包含完整的 DDL (Data Definition Language) 以便檢閱者建立資料庫結構。
- 可以在 Postman 匯入 `course-management.postman.collection.json`

## 開發環境

- Spring Boot：2.6.6
- Java：OpenJDK 11
- Gradle
- Lombok