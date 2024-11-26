"use client";
import { useEffect, useState } from "react";
import NavigationComponent from "@/components/navigationComponent";
import Image from "next/image";
import Link from "next/link";
import apiConfig from "@/assets/apiConfig";
import * as img from "../../assets/images";

export default function Courses() {
    const [courses, setCourses] = useState([]);

    async function fetchCourses() {
        try {
            const url = apiConfig.getUserCourses;
            const response = await fetch(url, {
                method: "GET",
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            });
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const data = await response.json();
            console.log(data);
            setCourses(data);
        } catch (error) {
            console.log(error);
        }
    }    

    useEffect(() => {
        fetchCourses();
    }, []);

    return (
        <>
            <header className="mb-16">
                <NavigationComponent />
            </header>
            <main className="container">
                {courses.map((course) => (
                    <Link key={course.id} href={`/courses/${course.id}`}>
                        <div className="flex items-center mb-4 gap-x-9 bg-customColor1 p-4 rounded-2xl">
                            <div className="flex-shrink-0 w-52 h-28">
                                {course.image ? (
                                    <Image 
                                        src={course.image} 
                                        width={210} 
                                        height={110} 
                                        alt={course.title || "Course image"}
                                        className="w-auto h-full max-w-full" 
                                    />
                                ) : (
                                    <Image 
                                        src={img.course} 
                                        width={210} 
                                        height={110} 
                                        alt={course.title || "Course image"}
                                        className="w-auto h-full max-w-full rounded-2xl" 
                                    />
                                )}
                            </div>
                            <div className="flex-1">
                                <h2 className="font-semibold text-customColor7 text-xl">{course.name}</h2>
                                <p className="font-medium text-customColor4">{course.description}</p>
                            </div>
                        </div>
                    </Link>
                ))}
            </main>
        </>
    );
}
