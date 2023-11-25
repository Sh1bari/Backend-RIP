function deleteEvent(id) {
    if (confirm("Вы уверены, что хотите удалить мероприятие?")) {
        // Создайте объект XMLHttpRequest
        var xhr = new XMLHttpRequest();

        // Откройте DELETE-запрос по указанному пути
        xhr.open("DELETE", '/api/admin/event/' + id, true);

        // Установите обработчик события, который будет вызван при завершении запроса
        xhr.onload = function () {
            if (xhr.status === 204) {
                // Здесь можно добавить обработку успешного удаления, если нужно
                location.reload(); // Обновить страницу после удаления
            } else {
                alert('Ошибка при удалении мероприятия: ' + xhr.statusText);
            }
        };

        // Отправьте DELETE-запрос
        xhr.send();
    }
}