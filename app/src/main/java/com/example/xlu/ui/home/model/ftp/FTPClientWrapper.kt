package com.example.xlu.ui.home.model.ftp

import android.content.Context
import android.net.Uri
import com.example.xlu.ui.utils.Credentials
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient

class FTPClientWrapper {
    private val ftpClient: FTPClient by lazy { FTPClient() }
    private val key = Credentials

    fun connect() {
        ftpClient.connect(key.FTP_HOST, key.FTP_PORT)
        ftpClient.login(key.FTP_USER, key.FTP_PASSWORD)
        ftpClient.enterLocalPassiveMode()
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE)
    }

    fun uploadFile(uri: Uri, remoteFileName: String, remoteDirectoryPath: String, context : Context,codeUpdate: String):Boolean {
        val success: Boolean
        ftpClient.makeDirectory(remoteDirectoryPath)
        val inputStream = context.contentResolver.openInputStream(uri)
        val send = ftpClient.storeFile("$remoteDirectoryPath/$remoteFileName$codeUpdate.jpg", inputStream)
        success = send
        inputStream?.close()
        return success
    }

    fun disconnect() {
        ftpClient.logout()
        ftpClient.disconnect()
    }
}