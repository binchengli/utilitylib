package com.meetingreat.mgwall.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;

import java.io.Reader;
import java.lang.reflect.Modifier;

/**
 * Created by Gregory on 2016/6/2.
 */
public class GsonHelper
{



	@NonNull public static String create(@NonNull Object object) {
		return create(true, true, object);
	}

	@NonNull public static String create(boolean excludeWithoutExpose, boolean serializeNull,
                                         @NonNull Object object) {
		return gsonBuild(excludeWithoutExpose, serializeNull, null).toJson(object);
	}

	@Nullable
	public static String create(@NonNull Object object, Class<?> aClass, TypeAdapter typeAdapter) {
		return create(false, true, object, aClass, typeAdapter);
	}

	@Nullable public static String create(boolean excludeWithoutExpose, boolean serializeNull,
                                          @NonNull Object object, Class<?> aClass, TypeAdapter typeAdapter) {
		return gsonBuild(excludeWithoutExpose, serializeNull,
				new TypeAdapterOptions(aClass, typeAdapter)).toJson(object);
	}

	public static String createNormalJson(Object objSrc) {
		Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.serializeNulls()
				.create();

		return gson.toJson(objSrc);
	}

	public static String createSimpleJson(Object objSrc) {
		Gson gson = new Gson();

		return gson.toJson(objSrc);
	}

	public static <T> T parserNormal(String strJSON, Class<T> cls) {
		if ((strJSON == null) || (strJSON.length() == 0)) return null;

		GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.serializeNulls();
		// gsonBuilder.enableComplexMapKeySerialization();
		Gson gson = gsonBuilder.create();

		try {
			return gson.fromJson(strJSON, cls);
		} catch(JsonSyntaxException ex) {
			return null;
		}
	}



	@Nullable public static <T> T parse(@Nullable String json, @NonNull Class<T> aClass)
			throws JsonSyntaxException {
		return parse(true, true, json, aClass);
	}

	@Nullable public static <T> T parse(boolean excludeWithoutExpose, boolean serializeNulls,
                                        @Nullable String json, @NonNull Class<T> aClass) throws JsonSyntaxException {
		if (json == null || json.isEmpty()) {
			return null;
		}

		try {
			return gsonBuild(excludeWithoutExpose, serializeNulls, null).fromJson(json, aClass);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Nullable
	public static <T> T parse(@Nullable Reader reader, @NonNull Class<T> aClass)
			throws JsonSyntaxException {
		return parse(false, true, reader, aClass);
	}

	@Nullable public static <T> T parse(boolean excludeWithoutExpose, boolean serializeNulls,
                                        @Nullable Reader reader, @NonNull Class<T> aClass) throws JsonSyntaxException {
		if (reader == null) {
			return null;
		}

		try {
			return gsonBuild(excludeWithoutExpose, serializeNulls, null).fromJson(reader, aClass);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Gson gsonBuild(boolean excludeWithoutExpose, boolean serializeNulls,
								 TypeAdapterOptions typeAdapterOptions) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		if (excludeWithoutExpose) {
			gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		}

		if (serializeNulls) {
			gsonBuilder.serializeNulls();
		}

		if (typeAdapterOptions != null) {
			gsonBuilder.registerTypeAdapter(typeAdapterOptions.aClass, typeAdapterOptions.typeAdapter);
		}
		gsonBuilder.excludeFieldsWithModifiers(Modifier.PROTECTED);
		return gsonBuilder.create();
	}

	private static final class TypeAdapterOptions {

		private Class aClass;
		private TypeAdapter typeAdapter;

		TypeAdapterOptions(Class aClass, TypeAdapter typeAdapter) {
			this.aClass = aClass;
			this.typeAdapter = typeAdapter;
		}
	}

}
