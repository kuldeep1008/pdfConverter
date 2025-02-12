function openUploader(toolType) {
    document.getElementById("fileUploader").style.display = "block";
    document.getElementById("fileUploader").setAttribute("data-tool", toolType);
}

function uploadFile() {
    let fileInput = document.getElementById("fileInput");
    let file = fileInput.files[0];
    let toolType = document.getElementById("fileUploader").getAttribute("data-tool");

    if (!file) {
        alert("Please select a file.");
        return;
    }

    let formData = new FormData();
    formData.append("file", file);

    let endpoint = "";
    switch (toolType) {
        case "pdfToWord": endpoint = "/api/pdf-to-word"; break;
        case "wordToPdf": endpoint = "/api/word-to-pdf"; break;
        case "imageToPdf": endpoint = "/api/image-to-pdf"; break;
        case "imageToWord": endpoint = "/api/image-to-word"; break;
    }

    fetch(endpoint, {
        method: "POST",
        body: formData
    })
    .then(response => response.blob())
    .then(blob => {
        let url = window.URL.createObjectURL(blob);
        let a = document.createElement("a");
        a.href = url;
        a.download = "converted-file";
        document.body.appendChild(a);
        a.click();
        document.getElementById("status").innerText = "Download completed!";
    })
    .catch(() => alert("Error in file conversion."));
}
