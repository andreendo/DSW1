async function retrieveAllBoards() {
    const response = await fetch('http://localhost:3000/board');
    const boards = await response.json();
    boards.forEach(board => {
        console.log(`Board ${board.id}: ${board.name} (${board.messages.length} messages)`);
    });
}

async function createBoard(name) {
    const response = await fetch('http://localhost:3000/board', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name })
    });
    const board = await response.json();
    console.log(`Created board ${board.id}: ${board.name}`);
}

async function deleteBoard(id) {
    await fetch(`http://localhost:3000/board/${id}`, {
        method: 'DELETE'
    });
    console.log(`Deleted board ${id}`);
}

retrieveAllBoards();

// createBoard('New Board');

// deleteBoard(1);

