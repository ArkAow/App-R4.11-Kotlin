package com.example.application_weather_report.android.model

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("dateGenerated")
	val dateGenerated: String? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("version")
	val version: String? = null,

	@field:SerializedName("user")
	val user: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("parameter")
	val parameter: String? = null,

	@field:SerializedName("coordinates")
	val coordinates: List<CoordinatesItem?>? = null
)

data class DatesItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("value")
	val value: Any? = null
)

data class CoordinatesItem(

	@field:SerializedName("lon")
	val lon: Any? = null,

	@field:SerializedName("dates")
	val dates: List<DatesItem?>? = null,

	@field:SerializedName("lat")
	val lat: Any? = null
)
