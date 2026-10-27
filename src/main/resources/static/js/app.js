$(function () {
    var $list = $('#todoList');
    var $message = $('#message');

    function showMessage(text, isError) {
        $message.text(text).toggleClass('error', !!isError);
    }

    function render(todos) {
        $list.empty();
        $.each(todos, function (_, todo) {
            var $item = $('<li>').attr('data-id', todo.id).toggleClass('done', todo.done);
            var $check = $('<input type="checkbox" class="toggle">').prop('checked', todo.done);
            var $title = $('<span class="title">').text(todo.title);
            var $delete = $('<button type="button" class="delete">삭제</button>');
            $list.append($item.append($check, $title, $delete));
        });
        var remaining = todos.filter(function (t) { return !t.done; }).length;
        $('#todoCount').text('전체 ' + todos.length + '개 · 남은 일 ' + remaining + '개');
    }

    function load() {
        $.getJSON('/api/todos').done(render).fail(function () {
            showMessage('목록을 불러오지 못했습니다.', true);
        });
    }

    $('#todoForm').on('submit', function (e) {
        e.preventDefault();
        var title = $.trim($('#todoTitle').val());
        $.ajax({
            url: '/api/todos',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ title: title })
        }).done(function (todo) {
            $('#todoTitle').val('').focus();
            showMessage('"' + todo.title + '"을(를) 추가했습니다.');
            load();
        }).fail(function (xhr) {
            showMessage((xhr.responseJSON && xhr.responseJSON.message) || '추가하지 못했습니다.', true);
        });
    });

    $list.on('change', '.toggle', function () {
        var id = $(this).closest('li').data('id');
        $.ajax({ url: '/api/todos/' + id + '/toggle', method: 'PATCH' }).done(load);
    });

    $list.on('click', '.delete', function () {
        var id = $(this).closest('li').data('id');
        $.ajax({ url: '/api/todos/' + id, method: 'DELETE' }).done(function () {
            showMessage('삭제했습니다.');
            load();
        });
    });

    load();
});
