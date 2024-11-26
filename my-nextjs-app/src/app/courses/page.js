"use client";
import { useEffect, useState } from "react";
import NavigationComponent from "@/components/navigationComponent";
import Image from "next/image";
import Link from "next/link";


export default function Courses() {
    const [courses, setCourses] = useState([]);

    const fetchCourses = async () => {
        try {
            const response = await fetch('/jsons/courses.json');
            const data = await response.json();
            setCourses(data);
        } catch (error) {
            console.log(error);
        }
    };

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
                        <div key={course.id} className="flex items-center mb-4 gap-x-9 bg-customColor1 p-4 rounded-2xl">
                            <Image 
                                src={course.image} 
                                width={210} 
                                height={110} 
                                alt={course.title} 
                                className="w-auto h-28 max-w-full" 
                            />
                            <div>
                                <h2 className="font-semibold text-customColor7 text-xl">{course.title}</h2>
                                <p className="font-medium text-customColor4">{course.description}</p>
                            </div>
                        </div>
                    </Link>
                ))}
            </main>
        </>
    );
}

