package com.aditya.appsatipadang.repository


import com.aditya.appsatipadang.data.local.UserLocal
import com.aditya.appsatipadang.data.local.UserPreference
import com.aditya.appsatipadang.data.remote.request.KirimTeknisiRequest
import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.remote.response.AddUserRequest
import okhttp3.RequestBody
import javax.inject.Inject


class DataRepository @Inject constructor(
    private val remoteData: RemoteDataSource,
    private val localData: UserPreference
) {
    fun loginUser(request: LoginRequest) = remoteData.loginUser(request)

    fun getUser() = localData.getUser()
    suspend fun saveUser(userLocal: UserLocal) = localData.saveUser(userLocal)

    suspend fun deleteUser() = localData.deleteUser()




    fun getListLaporan(token: String) = remoteData.getListLaporan(token)
    fun getListPenyerahan(token: String) = remoteData.getListPenyerahan(token)




    fun getLaporanStatus(token: String, status: String) = remoteData.getLaporanStatus(token, status)

    fun getListPengerjaan(token: String) = remoteData.getListPengerjaan(token)

    fun getListLaporanHarian(token: String) = remoteData.getListLaporanHarian(token)

    fun getListLaporanBulanan(token: String) = remoteData.getListLaporanBulanan(token)

    fun getHistoryDihapus(token: String) = remoteData.getHistoryDihapus(token)

    fun getTeknisiList(token: String) = remoteData.getTeknisiList(token)

    fun getNoPelaporan(token: String) = remoteData.getNoPelaporan(token)

    fun insertLaporanTeknisi(
        token: String,
        kirimTeknisiRequest: KirimTeknisiRequest
    ) = remoteData.insertLaporanTeknisi(token, kirimTeknisiRequest)





    fun inputLaporan(
        token: String,
        requestBody: RequestBody
    ) = remoteData.insertLaporan(token, requestBody)


    fun deleteLaporan(token: String, id: String, requestBody : RequestBody) = remoteData.deleteLaporan(token, id, requestBody)

    fun deletePenyerahan(token: String, id: String) = remoteData.deletePenyerahan(token, id)


    fun insertFoto(
        token: String,
        requestBody: RequestBody
    ) = remoteData.inputFotoProfil(token, requestBody)

    fun getLaporanId(
        token: String,
        id: String
    ) = remoteData.getLaporanId(token, id)


    fun getPenyerahanId(token: String, id: String) = remoteData.getPenyerahanId(token, id)

    fun getDataLaporan(token: String, id: String) = remoteData.getLaporanId(token, id)

    fun inputPenyerahan(
        token: String,
        requestBody: RequestBody
    ) = remoteData.inputPenyerahan(token, requestBody)

    fun kirimLaporanPerbaikan(
        token: String,
        requestBody: RequestBody
    ) = remoteData.kirimLaporanPerbaian(token, requestBody)

    // teknisi
    fun getLaporanHarianTeknisi(token: String) = remoteData.getListLaporanharianTeknisi(token)
    fun getLaporanBulananTeknisi(token: String) = remoteData.getListLaporanbulananTeknisi(token)

    fun getDataUser(token: String) = remoteData.getUserProfile(token)

    fun insertUser(token: String, request: AddUserRequest) =
        remoteData.insertUser(token, request)

    fun gantiPassword(
        requestBody: RequestBody
    ) = remoteData.gantiPassword(requestBody)
}