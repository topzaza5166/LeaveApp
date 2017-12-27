package com.vertice.teepop.leaveapp.data.model

import java.util.*

/**
 * Created by topza on 12/27/2017.
 */
data class LeaveAndType(var id: Int = 0,
                        var userId: Int = 0,
                        var typeId: Int = 0,
                        var typeName: String = "",
                        var leaveDate: Date = Date(),
                        var fromDate: Date = Date(),
                        var toDate: Date = Date(),
                        var timeLate: Int = 0,
                        var reason: String = "",
                        var approve: Int = 0,
                        var leaveStatus: Int = 0,
                        var createdDate: Date = Date()) {
}