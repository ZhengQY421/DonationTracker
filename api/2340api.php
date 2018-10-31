<?php
    $src = $_POST['src'];
    $user = $_POST['user'];
    $pass = $_POST['pass'];
    $conn = new mysqli("localhost", "root", "that-aint-it-chief", "youngmoney");
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }
    if (strcmp($src, "locations") == 0) {
        $sql = "SELECT * FROM locations";
        $result = $conn->query($sql);

        if ($result->num_rows > 0) {
            echo json_encode($result);
        } else {
            echo "0 results";
        }
    }
    $conn->close();
?>
