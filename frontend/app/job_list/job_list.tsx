import React from 'react'

interface Job {
    id: number
    title: string
    company: string
    location: string
    status: string
    dateApplied: string
}

type MainProps = { jobs: Job[] }

export function JobListTable() {
    return (
        <>
            <div>JobListTable</div>
            <JobList jobs={JOBS} />
        </>
    )
}

function JobList({ jobs }: MainProps) {
    console.log(jobs)

    return jobs.map((job) => <JobRow job={job} />)
}

type RowProps = { job: Job }

function JobRow({ job }: RowProps) {
    console.log(job)
    return <li><span>{job.title}</span> | <span>{job.company}</span></li>
}

const JOBS = [
    {
        id: 31,
        title: 'SWE',
        company: 'Atlassian',
        location: 'NYC',
        status: 'Waiting',
        dateApplied: '2025-05-10T14:25:02.058614',
    },

    {
        id: 4,
        title: 'SWE Backend',
        company: 'Google',
        location: 'Chicago',
        status: 'Waiting',
        dateApplied: '2025-04-02:25:02.058614',
    },
]
