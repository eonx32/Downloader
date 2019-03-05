const express = require('express')
const router = express.Router()
const fs = require('fs')
const dir = ('downloads/')

router.get('', (req, res) => {
    let filePath = req.query.path
    var file = dir+filePath
    if(fs.existsSync(file) === false) {
        res.status(404).send("File Not Found!")
    } else {
        res.download(file)
    }
});

module.exports = router;