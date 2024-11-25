import AccordionComponent from "@/components/accardionComponent";
import NavigationComponent from "@/components/navigationComponent";
import Image from "next/image";
import Link from "next/link";

const CourseDetail = ({ course, accordionData }) => {
    return (
        <>
            <header className="mb-8">
                <NavigationComponent />
            </header>
            <main className="container">
                <p className='font-medium text-customColor4'>
                    <Link href="/courses" className='text-customColor7 pl-4'>Курсы </Link>/ {course.title}
                </p>
                <div key={course.id} className="flex items-center gap-x-9 bg-customColor1 p-4 rounded-2xl mb-20">
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
                <AccordionComponent accordionData={accordionData} />
            </main>
        </>
    );
};

export async function generateStaticParams() {
    const response = await fetch('http://localhost:3000/jsons/courses.json');
    const courses = await response.json();

    return courses.map(course => ({
        id: course.id.toString(),
    }));
}

export default async function Page({ params }) {
    const response = await fetch('http://localhost:3000/jsons/courses.json');
    const courses = await response.json();
    const course = courses.find(course => course.id.toString() === params.id);


    const accordionResponse = await fetch('http://localhost:3000/jsons/accordionData.json');
    const accordionData = await accordionResponse.json();

    return <CourseDetail course={course} accordionData={accordionData.accordion} />;
}