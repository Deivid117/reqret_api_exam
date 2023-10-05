package com.dwh.regres_evaluation.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.dwh.gamesapp.data.api.ApiService
import com.dwh.regres_evaluation.data.database.dao.UsersDao
import com.dwh.regres_evaluation.data.database.entities.UsersEntity
import com.dwh.regres_evaluation.data.database.entities.toDatabase
import com.dwh.regres_evaluation.data.model.request.toData
import com.dwh.regres_evaluation.domain.model.request.CreateUser
import com.dwh.regres_evaluation.domain.model.request.UpdateUser
import com.dwh.regres_evaluation.domain.model.request.UserLogin
import com.dwh.regres_evaluation.domain.model.request.UserRegister
import com.dwh.regres_evaluation.domain.model.response.*
import com.dwh.regres_evaluation.domain.repository.UserRepository
import com.dwh.regres_evaluation.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.M)
class UserRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val networkUtils: NetworkUtils,
    private val usersDao: UsersDao
): UserRepository {

    override suspend fun userLogin(userLogin: UserLogin): Flow<UserToken> {
        if(!networkUtils.isWifiConnected()) {
            throw Exception("Sin conexión a internet")
        }
        val request = userLogin.toData()
        val responseApi = apiService.userLogin(request)
        if(responseApi.isSuccessful) {
            return flowOf(responseApi.body()!!.toDomain()).catch {
                Log.e("ResponseError", it.message ?: "Ocurrió un error en la respuesta de la API")
            }
        } else {
            val jsonObject = responseApi.errorBody()?.string()?.let { JSONObject(it) }
            val errorMsg = jsonObject?.getString("error")
            throw Exception(errorMsg)
        }
    }

    override suspend fun userRegister(userRegister: UserRegister): Flow<UserData> {
        if(!networkUtils.isWifiConnected()) {
            throw Exception("Sin conexión a internet")
        }
        val request = userRegister.toData()
        val responseApi = apiService.registerUser(request)
        if(responseApi.isSuccessful) {
            return flowOf(responseApi.body()!!.toDomain()).catch {
                Log.e("ResponseError", it.message ?: "Ocurrió un error en la respuesta de la API")
            }
        } else {
            val jsonObject = responseApi.errorBody()?.string()?.let { JSONObject(it) }
            val errorMsg = jsonObject?.getString("error")
            throw Exception(errorMsg)
        }
    }

    override suspend fun getUsersFromApi(): Flow<List<Users>> {
        val responseApi = apiService.getUsers(12)
        if(responseApi.isSuccessful) {
            return flowOf(
                apiService.getUsers(12).body()!!.data.map { it.toDomain() }
            ).catch {
                Log.e("ResponseError", it.message ?: "ocurrió un error en la respuesta de la API")
            }
        } else {
            val jsonObject = responseApi.errorBody()?.string()?.let { JSONObject(it) }
            val errorMsg = jsonObject?.getString("error")
            throw Exception(errorMsg)
        }
    }

    override suspend fun getUsersFromDatabase(): Flow<List<Users>> {
        val response = usersDao.getAllUsers()
        return flowOf(response.map { it.toDomain() }).catch {
            Log.e("UsersFromDB", it.message ?: "")
            emptyList<Users>()
        }
    }

    override suspend fun getAllUsers(): Flow<List<Users>> {
        return flow {
            val usersFromApi = try {
                getUsersFromApi()
            } catch (e: Exception) {
                Log.e("AllUsers", "API_ERROR ${e.message}")
                flowOf(emptyList<Users>())
            }
            if (usersFromApi.single().isNotEmpty()) {
                Log.i("AllUsers", "API_RESPONSE: Se obtuvieron los datos de la api")
                val users = usersFromApi.single().map { it.toDatabase() }
                try {
                    insertUsers(users)
                } catch (e: Exception) {
                    Log.e("AllUsers", e.message ?: "Error desconocido")
                }
                emit(usersFromApi.single())
            } else {
                Log.i("AllUsers", "API_RESPONSE: No hay datos por parte de la api")
                val usersFromDatabase =
                    getUsersFromDatabase().firstOrNull() ?: emptyList()
                if (usersFromDatabase.isNotEmpty()) {
                    emit(usersFromDatabase)
                    Log.i("AllUsers", "ROOM_RESPONSE: Obtuviste la información de la bd")
                } else {
                    Log.i("AllUsers", "ROOM_RESPONSE: La base de datos está vacía")
                    throw Exception("ERROR, no hay datos por parte de la api ni la base de datos")
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertUsers(usersEntity: List<UsersEntity>) {
        usersDao.insertUsers(usersEntity)
    }

    override suspend fun updateUser(id: Int, user: UpdateUser): Flow<UserUpdated> {
        if(!networkUtils.isWifiConnected()) {
            throw Exception("Sin conexión a internet")
        }
        val responseApi = apiService.userUpdate(id, user.toData())
        if(responseApi.isSuccessful) {
            return flowOf(
                responseApi.body()!!.toDomain()
            ).catch {
                Log.e("ResponseError", it.message ?: "ocurrió un error en la respuesta de la API")
            }
        } else {
            val jsonObject = responseApi.errorBody()?.string()?.let { JSONObject(it) }
            val errorMsg = jsonObject?.getString("error")
            throw Exception(errorMsg)
        }
    }

    override suspend fun createUserApi(user: CreateUser): Flow<UserCreated> {
        val responseApi = apiService.createUser(user.toData())
        if(responseApi.isSuccessful) {
            return flowOf(
                responseApi.body()!!.toDomain()
            ).catch {
                Log.e("ResponseError", it.message ?: "ocurrió un error en la respuesta de la API")
            }
        } else {
            val jsonObject = responseApi.errorBody()?.string()?.let { JSONObject(it) }
            val errorMsg = jsonObject?.getString("error")
            throw Exception(errorMsg)
        }
    }

    override suspend fun createUserDatabase(userEntity: UsersEntity): Long {
        return usersDao.createUser(userEntity)
    }

    override suspend fun createUser(user: CreateUser): Flow<UserCreated> {
        return flow {
            val successApi = try {
                createUserApi(user)
            } catch (e: Exception) {
                flowOf(UserCreated())
            }
            if (successApi.firstOrNull() != null) {
                Log.i("CreateUser", "API_RESPONSE: Se obtuvieron los datos de la api")
                try {
                    createUserDatabase(UsersEntity(firstName = user.name, job = user.job))
                    Log.i("CreateUser", "ROOM_RESPONSE: Se insertaron nuevos datos")
                } catch (e: Exception) {
                    Log.e("CreateUser", e.message ?: "Error desconocido")
                }
                emit(successApi.single())
            } else {
                var id = 0L
                try {
                    id = createUserDatabase(UsersEntity(firstName = user.name, job = user.job))
                    Log.i("CreateUser", "ROOM_RESPONSE: Se insertaron nuevos datos")
                } catch (e: Exception) {
                    Log.e("CreateUser", e.message ?: "Error desconocido")
                }
                emit(UserCreated(id = id.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteUserFromApi(id: Int): Flow<UserDeletedData> {
        val responseApi = apiService.deleteUser(id)
        if(responseApi.isSuccessful) {
            return flowOf(
                if (responseApi.body() != null) {
                    responseApi.body()?.data?.toDomain() ?: UserDeletedData()
                } else {
                    UserDeletedData()
                }
            ).catch {
                Log.e("ResponseError", it.message ?: "ocurrió un error en la respuesta de la API")
            }
        } else {
            val jsonObject = responseApi.errorBody()?.string()?.let { JSONObject(it) }
            val errorMsg = jsonObject?.getString("error")
            Log.e("ResponseError", errorMsg.toString())
            throw Exception(errorMsg)
        }
    }

    override suspend fun deleteUserFromDatabase(id: Int) {
        usersDao.deleteUserFromDatabase(id)
    }

    override suspend fun deleteUser(id: Int): Flow<UserDeletedData> {
        return flow {
            val successApi = try {
                deleteUserFromApi(id)
            } catch (e: Exception) {
                flowOf(UserDeletedData())
            }
            if (successApi.firstOrNull() != null) {
                Log.i("DeleteUser", "API_RESPONSE: Se obtuvieron los datos de la api")
                try {
                    deleteUserFromDatabase(id)
                    Log.i("DeleteUser", "ROOM_RESPONSE: Se borraron los datos")
                } catch (e: Exception) {
                    Log.e("DeleteUser", e.message ?: "Error desconocido")
                }
                emit(successApi.single())
            } else {
                Log.i("DeleteUser", "API_RESPONSE: Hubo un problema con la conexión a la api")
                try {
                    deleteUserFromDatabase(id)
                    Log.i("DeleteUser", "ROOM_RESPONSE: Se borraron los datos")
                } catch (e: Exception) {
                    Log.e("DeleteUser", e.message ?: "Error desconocido")
                }
                emit(UserDeletedData())
            }
        }.flowOn(Dispatchers.IO)
    }
}