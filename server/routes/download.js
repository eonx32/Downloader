
const express = require('express')
const fs = require('fs')
const download = require('download')
const split = require('split-file')
const router = express.Router()
const regUrl = (/^([a-z]+:\/\/.+)(\.\w+)$/i)
const dir = ('downloads/')

var getUniqueFilename = () => {
    let currTime = new Date().getTime()
    return (dir+currTime)
}

var downloadAndStore = async (url, filePath) => {
    var data = await download(url)
    fs.writeFileSync(filePath, data)
}

var storeFile = async function(req, res) {
    try{
        let url = req.body.url
        let parts = req.body.parts

        const [, , ext] = url.match(regUrl)
        const filePath = getUniqueFilename()+ext

        await downloadAndStore(url, filePath)
        let fileLocations = await split.splitFile(filePath, parts)
        
        let fileNames = []
        fileLocations.forEach(location => {
            let [, , name] = location.match(/^([a-z]+\/)(.*)$/i)
            fileNames.push(name)
        });

        res.status(200).send({"file": fileNames})
    }catch(err ){
        console.log(err)
        res.status(500).send(err)
    }
};

router.post('/', (req, res)=> {
    storeFile(req, res)
})

module.exports = router;